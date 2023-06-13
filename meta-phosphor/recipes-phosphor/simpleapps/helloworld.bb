SUMMARY = "Google Codelab Helloworld Application"
DESCRIPTION = "Google Codelab Helloworld Application."
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

inherit autotools pkgconfig systemd

DEPENDS += " \
  autoconf-archive-native \
  phosphor-dbus-interfaces \
  phosphor-logging \
  sdbusplus \
  sdeventplus \
"

# Normally we also specify a SRC_URI, SRCREV, and other variables.
