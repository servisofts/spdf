import React, { Component } from 'react';
import { SDate, SHr, SImage, SList, SLoad, SMath, SNavigation, SText, STheme, SView } from 'servisofts-component';
import SSocket from 'servisofts-socket'
import Model from '../../Model';
// props = {disabled}
export default class Garante extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }
    data = {}

    seleccionarCliente() {
        SNavigation.navigate("/cliente", {
            onSelect: (obj) => {
                var obj = {
                    nit: obj.CI,
                    razon_social: obj.Nombres + " " + obj.Apellidos,
                    key_usuario: obj.key,
                    telefono: obj.Telefono,
                    correo: obj.Correo,
                    direccion: "",
                    key_usuario: obj.key,
                    // sucursal: "SUCURSAL TODO",
                }
                this.data.garante = obj;
                Model.compra_venta.Action.editar({
                    data: this.data,
                    key_usuario: Model.usuario.Action.getKey()
                }).then((resp) => {
                    console.log("Se agrego el cliente con exito")
                })
            }
        })
    }
    render() {
        if (this.props.data.tipo_pago == "contado") return null;
        this.empresa = Model.empresa.Action.getSelect();
        this.data = this.props.data;
        if (!this.data?.garante) {
            if (this.props.disabled) {
                return <SView>
                    <SHr height={16} />
                    <SText>{"SIN GARANTE"}</SText>
                    <SHr height={16} />
                </SView>
            }
            return <SView col={"xs-12"} center>
                <SHr height={24} />
                <SView style={{
                    padding: 16
                }} card onPress={() => {
                    this.seleccionarCliente()
                }}>
                    <SText bold color={STheme.color.danger} >{"SELECCIONE EL GARANTE"}</SText>
                </SView>
                <SHr height={24} />
            </SView>
        }

        var { nit, razon_social, telefono, correo, direccion, key_usuario } = this.data.garante
        var onPress;
        if (!this.props.disabled) {
            onPress = this.seleccionarCliente.bind(this)
        }
        var urlFoto = "";
        if (key_usuario) {
            urlFoto = SSocket.api.root + "usuario/" + key_usuario;
        }
        return <SView col={"xs-12"} center >
            <SText color={STheme.color.lightGray} col={"xs-12"}>Garante</SText>
            <SHr />
            <SView col={"xs-12"} center onPress={onPress}>
                <SView width={40} height={40} style={{ padding: 4 }}>
                    <SView flex height card>
                        <SImage src={urlFoto} />
                    </SView>
                </SView>
                <SHr />
                <SText center col={"xs-10"}>{razon_social}</SText>
                <SHr />
                <SText center col={"xs-10"}>{`Nit. ${nit}`}</SText>
                <SText center col={"xs-10"}>{telefono ? `Tel. ${telefono}` : ""}</SText>
                <SText center col={"xs-10"}>{correo}</SText>
                <SText center col={"xs-10"}>{direccion}</SText>
            </SView>

            <SHr />
        </SView>
    }
}
