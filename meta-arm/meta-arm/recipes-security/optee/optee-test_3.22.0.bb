require optee-test.inc

SRC_URI += " \
    file://musl-workaround.patch \
   "
SRCREV = "a286b57f1721af215ace318d5807e63f40186df6"

EXTRA_OEMAKE:append = " OPTEE_OPENSSL_EXPORT=${STAGING_INCDIR}"
DEPENDS:append = " openssl"
CFLAGS:append = " -Wno-error=deprecated-declarations"
