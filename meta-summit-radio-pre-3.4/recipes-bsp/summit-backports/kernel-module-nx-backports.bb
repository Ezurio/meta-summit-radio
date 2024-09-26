SUMMARY = "Summit Backports for NX61x"

BACKPORTS_CONFIG = "${@bb.utils.contains('DISTRO_FEATURES','bluetooth','sona_nx611','sona_nx611_nbt',d)}"

RCONFLICTS_${PN} = " \
        kernel-module-60-backports \
        kernel-module-lwb-if-backports \
        kernel-module-ti-backports \
        kernel-module-msd45-backports \
        kernel-module-msd50-backports \
        kernel-module-bdsdmac-backports \
        "

module_conf_moal = "options moal mod_para=nxp/wifi_prod_params.conf"

KERNEL_MODULE_PROBECONF += "moal"

require summit-backports.inc
require radio-stack-nx-version.inc
