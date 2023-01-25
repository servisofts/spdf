import React, { Component } from 'react';
import { SHr, SLoad, SText, STheme, SView } from 'servisofts-component';
import Model from '../../../Model';
import TipoDePago from '../TipoDePago';
import TipoPago from './TipoPago';
import tipo_pago_tipos from './tipo_pago_tipos';
export default class PlanDePagos extends Component {
    constructor(props) {
        super(props);
        this.state = {
            tipo_pago: this.props.data?.tipo_pago ?? "contado"
        };
    }

    guardarCuotas() {
        const { info, arr } = this.tipo_pago_select.getCuotas()
        return Model.cuota.Action.registroAll({
            key_compra_venta: this.props.data.key,
            periodicidad_medida: info?.periodicidad_medida,
            periodicidad_valor: info?.periodicidad_valor,
            key_usuario: Model.usuario.Action.getKey(),
            porcentaje_interes: info?.porcentaje_interes,
            tipo_pago: info.tipo_pago,
            data: arr
        })
    }
    getCuotas() {
        return this.tipo_pago_select.getCuotas();
    }
    getOptions() {
        var tipo = tipo_pago_tipos[this.state.tipo_pago];
        if (!tipo) return <SText>Error</SText>
        var TipoPagoComponent = tipo.ComponentOpciones
        // console.log(tipo.calcular_cuotas())
        // console.log(this.props.data)
        if (!this.cuotas) return <SLoad />
        return <SView col={"xs-12"}>
            <TipoPagoComponent ref={ref => this.tipo_pago_select = ref} data={this.props.data} cuotas={this.cuotas} totales={this.totales} />
        </SView>
    }
    render() {
        this.totales = Model.compra_venta_detalle.Action.getTotales({
            key_compra_venta: this.props.data.key
        })
        this.cuotas = Model.cuota.Action.getAllByKeyCompraVenta({
            key_compra_venta: this.props.data.key
        })
        return (
            <SView col={"xs-12"} center>
                {/* <TipoPago ref={ref => this.tipo_pago = ref} defaultValue={this.state.tipo_pago} onChange={(e) => {
                    this.setState({ tipo_pago: e })
                }} /> */}
                <TipoDePago data={this.props.data} onChange={(e) => {
                    this.setState({ tipo_pago: e })
                }} />
                <SHr />
                {this.getOptions()}
                <SHr />
                <SHr />

                {/* <Recargar data={this.props.data} tipo_pago={this.state.tipo_pago} /> */}
                {/* <ListaCuotas data={this.props.data} /> */}

            </SView>
        );
    }
}
