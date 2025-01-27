#! /bin/sh

set -e

dir=$(dirname "${0}")

tmpdir="$(mktemp -d -t calc-hash-XXXXXX)"
export tmpdir

for i in 60 lwb msd mt nx ti bdsdmac
do
  "${dir}/calc-hash-${i}.sh" "${1}" &
done

wait

exit_code=$?

rm -rf "${tmpdir}"

[ ${exit_code} -eq 0 ] || exit 1

