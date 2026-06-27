package dev.jakubzika.befair.data.storage

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.readBytes
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.value
import platform.CoreFoundation.CFDictionaryAddValue
import platform.CoreFoundation.CFDictionaryCreateMutable
import platform.CoreFoundation.CFRelease
import platform.CoreFoundation.CFTypeRefVar
import platform.CoreFoundation.kCFAllocatorDefault
import platform.CoreFoundation.kCFBooleanTrue
import platform.Foundation.CFBridgingRelease
import platform.Foundation.CFBridgingRetain
import platform.Foundation.NSData
import platform.Foundation.create
import platform.Security.SecItemAdd
import platform.Security.SecItemCopyMatching
import platform.Security.SecItemDelete
import platform.Security.errSecSuccess
import platform.Security.kSecAttrAccount
import platform.Security.kSecAttrService
import platform.Security.kSecClass
import platform.Security.kSecClassGenericPassword
import platform.Security.kSecMatchLimit
import platform.Security.kSecMatchLimitOne
import platform.Security.kSecReturnData
import platform.Security.kSecValueData

/**
 * iOS [TokenStorage] backed by the Keychain (`kSecClassGenericPassword`). Each token is a
 * generic password item keyed by a fixed service + per-token account name.
 */
@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual class TokenStorage actual constructor() {

    private val service = "dev.jakubzika.befair.auth"

    actual fun saveTokens(access: String, refresh: String) {
        save(TOKEN_KEY_ACCESS, access)
        save(TOKEN_KEY_REFRESH, refresh)
    }

    actual fun getAccessToken(): String? = load(TOKEN_KEY_ACCESS)

    actual fun getRefreshToken(): String? = load(TOKEN_KEY_REFRESH)

    actual fun clearTokens() {
        delete(TOKEN_KEY_ACCESS)
        delete(TOKEN_KEY_REFRESH)
    }

    private fun save(account: String, value: String) {
        delete(account)
        val serviceRef = CFBridgingRetain(service)
        val accountRef = CFBridgingRetain(account)
        val dataRef = CFBridgingRetain(value.toNSData())
        val query = CFDictionaryCreateMutable(kCFAllocatorDefault, 0, null, null)
        CFDictionaryAddValue(query, kSecClass, kSecClassGenericPassword)
        CFDictionaryAddValue(query, kSecAttrService, serviceRef)
        CFDictionaryAddValue(query, kSecAttrAccount, accountRef)
        CFDictionaryAddValue(query, kSecValueData, dataRef)

        SecItemAdd(query, null)

        CFRelease(query)
        CFBridgingRelease(serviceRef)
        CFBridgingRelease(accountRef)
        CFBridgingRelease(dataRef)
    }

    private fun load(account: String): String? {
        val serviceRef = CFBridgingRetain(service)
        val accountRef = CFBridgingRetain(account)
        val query = CFDictionaryCreateMutable(kCFAllocatorDefault, 0, null, null)
        CFDictionaryAddValue(query, kSecClass, kSecClassGenericPassword)
        CFDictionaryAddValue(query, kSecAttrService, serviceRef)
        CFDictionaryAddValue(query, kSecAttrAccount, accountRef)
        CFDictionaryAddValue(query, kSecReturnData, kCFBooleanTrue)
        CFDictionaryAddValue(query, kSecMatchLimit, kSecMatchLimitOne)

        val result = memScoped {
            val out = alloc<CFTypeRefVar>()
            val status = SecItemCopyMatching(query, out.ptr)
            if (status == errSecSuccess) {
                (CFBridgingRelease(out.value) as? NSData)?.toKString()
            } else {
                null
            }
        }

        CFRelease(query)
        CFBridgingRelease(serviceRef)
        CFBridgingRelease(accountRef)
        return result
    }

    private fun delete(account: String) {
        val serviceRef = CFBridgingRetain(service)
        val accountRef = CFBridgingRetain(account)
        val query = CFDictionaryCreateMutable(kCFAllocatorDefault, 0, null, null)
        CFDictionaryAddValue(query, kSecClass, kSecClassGenericPassword)
        CFDictionaryAddValue(query, kSecAttrService, serviceRef)
        CFDictionaryAddValue(query, kSecAttrAccount, accountRef)

        SecItemDelete(query)

        CFRelease(query)
        CFBridgingRelease(serviceRef)
        CFBridgingRelease(accountRef)
    }

    private fun String.toNSData(): NSData {
        val bytes = encodeToByteArray()
        if (bytes.isEmpty()) return NSData()
        return bytes.usePinned { pinned ->
            NSData.create(bytes = pinned.addressOf(0), length = bytes.size.toULong())
        }
    }

    private fun NSData.toKString(): String? {
        val length = length.toInt()
        if (length == 0) return ""
        val pointer = bytes ?: return null
        return pointer.readBytes(length).decodeToString()
    }
}
