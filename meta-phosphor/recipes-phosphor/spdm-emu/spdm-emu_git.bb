HOMEPAGE = "https://github.com/DMTF/spdm-emu"
SUMMARY = "spdm-emu contains a SPDM requester emulator and a SPDM responder emulator that can run in OS environment."
DESCRIPTION = "The SPDM is a security protocol that provides remote firmware attestation, and other security services. The spdm-emu repository includes an emulated SPDM Requester and an emulated SPDM Responder."

PR = "r1"
PV = "1.0+git${SRCPV}"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=cd2c281a3048799e5ed6d1ad8be1ebe1"

inherit cmake pkgconfig systemd

DEPENDS += " \
  phosphor-dbus-interfaces \
  phosphor-logging \
  sdbusplus \
  sdeventplus \
"

# Remove format check from default cmake flags as openssl don't want it.
OECMAKE_C_FLAGS:remove = "-Wformat -Wformat-security -Werror=format-security"
OECMAKE_CXX_FLAGS:remove = "-Wformat -Wformat-security -Werror=format-security"

# Force it to use make.
OECMAKE_GENERATOR = "Unix Makefiles"

# libspdm specific configs
#PACKAGECONFIG ?= "x64 mbedtls gcc release"
PACKAGECONFIG ?= "x64 openssl gcc release"

PACKAGECONFIG[x64] = "-DARCH=x64"
PACKAGECONFIG[openssl] = "-DCRYPTO=openssl"
#PACKAGECONFIG[mbedtls] = "-DCRYPTO=mbedtls"
PACKAGECONFIG[gcc] = "-DTOOLCHAIN=GCC"
PACKAGECONFIG[release] = "-DTARGET=Release"

# gitsm is required as libspdm has submodules like openssl.
S = "${WORKDIR}/git"
SRC_URI = "gitsm://github.com/DMTF/spdm-emu.git;branch=main;protocol=https"
SRCREV = "94668a9b76d39401b5c043552b7045ca505c87bc"

# libspdm requires `make copy_sample_key` before `make all`.
do_compile:prepend() {
	make copy_sample_key
}

#addtask sometask after do_configure before do_compile
