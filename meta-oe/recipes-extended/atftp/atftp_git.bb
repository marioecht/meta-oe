DESCRIPTION = "Advanced TFTP server and client"
SECTION = "network"
HOMEPAGE = "http://packages.debian.org/atftp"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=94d55d512a9ba36caa9b7df079bae19f"
PV = "0.7.1+git${SRCPV}"

SRCREV = "be3291a18c069ae23a124ffdc56d64a5ff0bbec7"

SRC_URI = "git://atftp.git.sourceforge.net/gitroot/atftp/atftp;protocol=git \
           file://atftpd-0.7_circumvent_tftp_size_restrictions.patch \
           file://atftpd-0.7_unprotected_assignments_crash.patch \
           file://atftpd.init \
          "
S = "${WORKDIR}/git"

inherit autotools update-rc.d useradd

INITSCRIPT_PACKAGES = "${PN}d"
INITSCRIPT_NAME_${PN}d = "atftpd"
INITSCRIPT_PARAMS_${PN}d = "defaults 80"

USERADD_PACKAGES = "${PN}d"
USERADD_PARAM_${PN}d = "--system --no-create-home --shell /bin/false \
                        --user-group nobody"

do_install_append() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/atftpd.init ${D}${sysconfdir}/init.d/atftpd

    install -d ${D}/srv/tftp

    rm ${D}${sbindir}/in.tftpd
}

PACKAGES =+ "atftpd"

FILES_${PN} = "${bindir}/*"

FILES_${PN}d = "${sbindir}/* ${sysconfdir}/init.d/* /srv/tftp"
