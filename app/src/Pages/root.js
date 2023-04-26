import React, { Component } from 'react';
import { connect } from 'react-redux';
import { SButtom, SHr, SIcon, SImage, SList, SLoad, SNavigation, SPage, SText, STheme, SView } from 'servisofts-component';
import Model from '../Model';
import SSocket from 'servisofts-socket'
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }

    componentDidMount() {
        this.getData();
    }
    getData() {
        this.setState({ loading: true })
        SSocket.sendHttpAsync(SSocket.api.servicio + "api", {
            component: "servicio",
            type: "getAllHabilitados",
            estado: "cargando",
            key_servicio: "12ea25f7-4e08-4ac5-9561-b7a87dc1bb82",
        }).then(e => {
            this.setState({ loading: false, data: e.data })
        }).catch(e => {
            this.setState({ loading: false })
            console.error(e);
        })
    }

    Item(obj) {
        const { certificado, descripcion, estado, ip, ip_public, nombre, puerto, puerto_ws, puerto_http, puerto_arduino } = obj;
        return <SView col={"xs-12"} card style={{ padding: 8 }} onPress={() => {
            SNavigation.navigate("/test");
        }}>
            <SText fontSize={18} bold>{nombre}</SText>
            <SText color={STheme.color.lightGray}>{ip}</SText>
            <SHr />
            <SView row>
                <SText color={STheme.color.lightGray}>{puerto}</SText>
                <SView width={8} />
                <SText color={STheme.color.lightGray}>{puerto_ws}</SText>
                <SView width={8} />
                <SText color={STheme.color.lightGray}>{puerto_http}</SText>
                <SView width={8} />
                <SText color={STheme.color.lightGray}>{puerto_arduino}</SText>
            </SView>
            {/* <SText>{JSON.stringify(obj)}</SText> */}
        </SView>
    }
    getLista() {
        return <SList
            buscador
            data={this.state.data}
            render={this.Item}
        />
    }

    render() {
        return (
            <SPage title={'SPDF'} onRefresh={() => {
                this.getData();
            }}>
                <SView col={"xs-12"} center>
                    <SView col={"xs-11 sm-10 md-8 lg-6 xl-4"} center>
                        {this.getLista()}
                    </SView>
                </SView>
            </SPage>
        );
    }
}
const initStates = (state) => {
    return { state }
};
export default connect(initStates)(index);