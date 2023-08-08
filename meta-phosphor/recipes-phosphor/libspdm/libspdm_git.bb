HOMEPAGE = "https://github.com/DMTF/libspdm"
SUMMARY = "libspdm is a sample implementation that follows the DMTF SPDM specifications"
DESCRIPTION = "The SPDM is a security protocol that provides remote firmware attestation, and other security services. The libspdm repository includes libraries that can be used to construct an SPDM Requester and an SPDM Responder."

PR = "r1"
PV = "1.0+git${SRCPV}"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=31d8b5ee5c0ad0dd6f6465d88d8caacd"

inherit cmake pkgconfig

# Remove format check from default cmake flags as openssl don't want it.
OECMAKE_C_FLAGS:remove = "-Wformat -Wformat-security -Werror=format-security"
OECMAKE_CXX_FLAGS:remove = "-Wformat -Wformat-security -Werror=format-security"

# Force it to use make.
OECMAKE_GENERATOR = "Unix Makefiles"

# libspdm specific configs
PACKAGECONFIG ?= "x64 openssl gcc release"

PACKAGECONFIG[x64] = "-DARCH=x64"
PACKAGECONFIG[openssl] = "-DCRYPTO=openssl"
PACKAGECONFIG[gcc] = "-DTOOLCHAIN=GCC"
PACKAGECONFIG[release] = "-DTARGET=Release"

# gitsm is required as libspdm has submodules like openssl.
S = "${WORKDIR}/git"
SRC_URI = "gitsm://github.com/DMTF/libspdm.git;branch=main;protocol=https"
SRCREV = "4f86fb082324d5777b7dd98ff1ff683d423b000a"

# libspdm requires `make copy_sample_key` before `make all`.
do_compile:prepend() {
	make copy_sample_key
}

#addtask sometask after do_configure before do_compile
