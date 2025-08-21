SUMMARY = "Bluetooth UART Startup scripts for 60 Series"

LICENSE = "Ezurio"
NO_GENERIC_LICENSE[Ezurio] = "LICENSE.ezurio"
LIC_FILES_CHKSUM = "file://LICENSE.ezurio;md5=fd3dd0630b215465b6f50540642d5b93"

inherit allarch systemd

BT_SERIAL_PORT ?= "/dev/ttyS1"
BT_SERIAL_BAUD ?= "3000000"

SRC_URI = " \
    file://LICENSE.ezurio;subdir=src \
    file://bt-service.sh;subdir=src \
    file://bttest.sh;subdir=src \
    file://80-btattach.rules-sysv;subdir=src \
    file://80-btattach.rules-sysd;subdir=src \
    file://btattach.service;subdir=src \
    "

S = "${WORKDIR}/src"

FILES_${PN} += "${systemd_unitdir}/system ${sysconfdir}"

SYSTEMD_SERVICE_${PN} = "btattach.service"

do_install() {
    install -D -m 0775 -t "${D}${bindir}" "${S}/bt-service.sh" "${S}/bttest.sh"

    install -d "${D}${sysconfdir}/default"
    echo "PORT=${BT_SERIAL_PORT}"  > "${D}${sysconfdir}/default/bt-service"
    echo "BAUD=${BT_SERIAL_BAUD}" >> "${D}${sysconfdir}/default/bt-service"

    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        install -D -m 0644 "${S}/80-btattach.rules-sysd" \
            "${D}${sysconfdir}/udev/rules.d/80-btattach.rules"
        install -D -m 0644 ${S}/btattach.service \
            "${D}${systemd_unitdir}/system/btattach.service"
    else
        install -D -m 0644 "${S}/80-btattach.rules-sysv" \
            "${D}${sysconfdir}/udev/rules.d/80-btattach.rules"
    fi
}
