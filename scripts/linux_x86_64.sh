#!/bin/bash
##################################
# InSilico Start Script
##################################
echo "Starting InSilico"

# directory of this script 
ROOTDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
cd $ROOTDIR/releng/org.insilico.product/target/products/insilico/linux/gtk/x86_64/insilico/
./InSilico
cd $ROOTDIR

