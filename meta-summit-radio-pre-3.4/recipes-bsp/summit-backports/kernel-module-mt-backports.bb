SUMMARY = "Summit Backports for Mediatek"

MTK_BACKPORTS_BUILD_MTPREALLOC ?= "n"

BACKPORTS_CONFIG = "${@bb.utils.contains('DISTRO_FEATURES','bluetooth','mt320','mt320_nbt',d)}"

RCONFLICTS_${PN} = " \
        kernel-module-60-backports \
        kernel-module-lwb-if-backports \
        kernel-module-ti-backports \
        kernel-module-msd45-backports \
        kernel-module-msd50-backports \
        kernel-module-bdsdmac-backports \
        "

require summit-backports.inc
require radio-stack-version.inc

EXTRA_OEMAKE += "CONFIG_BUILD_MTPREALLOC=${MTK_BACKPORTS_BUILD_MTPREALLOC}"
