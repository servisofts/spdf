import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Linking } from 'react-native';
import { SButtom, SHr, SPage, SText, SView } from 'servisofts-component';
import SSocket from 'servisofts-socket'
import Model from '../Model';
class Test extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }

    render() {
        return (
            <SPage title={'Test'} hidden>
                <SHr />
                <SButtom loading={this.state.loading} type='danger' onPress={() => {
                    this.setState({ loading: true })
                    SSocket.sendPromise({
                        component: "dato",
                        type: "pdf",
                        key_usuario: Model.usuario.Action.getKey(),
                        estado: "cargando",
                    }).then(resp => {
                        this.setState({ loading: false })
                        Linking.openURL(SSocket.api.root + "pdf/" + resp.data);
                        // console.log(resp);
                    }).catch(e => {
                        this.setState({ loading: false })
                        console.error(e)
                    })

                }}  >
                    ENVIAR PDF
                </SButtom>
            </SPage >
        );
    }
}
const initStates = (state) => {
    return { state }
};
export default connect(initStates)(Test);