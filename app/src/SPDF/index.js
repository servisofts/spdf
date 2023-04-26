import React, { Component } from 'react';
import { SPage, SScrollView2, SText, SView } from 'servisofts-component';
import ItemProperties from './Components/ItemProperties';
import Layers from './Components/Layers';
import WorkSpace from './Components/WorkSpace';

const defaultData = require("./comprobante.json");
export default class SPDF extends Component {
    constructor(props) {
        super(props);
        let data = this.props?.data ?? defaultData
        this.state = {
            select: data,
            data: data
        };
    }

    render() {
        return <SView col={"xs-12"} height row >
            <Layers {...this.props} parent={this} />
            <WorkSpace {...this.props} parent={this} />
            <ItemProperties {...this.props} parent={this} />
        </SView>
    }
}