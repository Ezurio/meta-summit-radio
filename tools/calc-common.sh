#! /bin/bash

set -e -o pipefail

: "${file:-?}"

[ -z "${ver}" ] && { echo "Usage: ${0} <version>"; exit 1; }

prefix="/var/www/html/builds/linux"
cmdopt="-o ControlMaster=auto -o ControlPersist=5s -o ControlPath=/tmp/ssh-fileshare fileshare@files.devops.rfpros.com"

calc_hash() {
  ssh ${cmdopt} "set -e; cd ${prefix} && ${1} ${2}" | \
    sed -r \
      -e 's/-src//g' \
      -e 's/_/-/g' \
      -e 's/summit-(.*)-firmware/\1-firmware/g' \
      -e "s/([0-9a-f]+)  .*\/(.*)-[0-9.]+\.tar.*/SRC_URI[\2.${1}] = \"\1\"/" \
       >> "${file}"
}

printf 'RADIO_VERSION = "%s"\n\n' "${ver}" > "${file}"
