#!/bin/bash
#scp ./servisofts.jks servisofts@192.168.0.10:/home/servisofts/servicios/compra_venta/entornos/compra_venta/servicios/compra_venta/
#scp ./config.json servisofts@192.168.0.10:/home/servisofts/servicios/compra_venta/entornos/compra_venta/servicios/compra_venta/
#scp ./servicio.pem servisofts@192.168.0.10:/home/servisofts/servicios/compra_venta/entornos/compra_venta/servicios/compra_venta/

scp ./server.jar servisofts@192.168.2.5:/home/servisofts/servicios/spdf/entornos/spdf/servicios/spdf/
scp -r ./font servisofts@192.168.2.5:/home/servisofts/servicios/spdf/entornos/spdf/servicios/spdf/