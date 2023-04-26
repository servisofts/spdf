import React, { Component } from 'react';
import { SList, SText, SView } from 'servisofts-component';

export default class Layers extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }


    renderName(obj) {
        if (obj.type == "text") return obj.childrens
        return obj.type;
    }
    renderChildrens(obj, lvl) {
        if (!obj.childrens) return;
        if (obj.type == "text") {
            return;
        }
        return obj.childrens.map((obj) => {
            return this.renderItem(obj, lvl);
        })
    }
    renderItem(obj, lvl = 0) {
        let select = this.props.parent.state.select === obj;
        return <SView col={"xs-12"} style={{
            padding: 4
        }}>
            <SView col={"xs-12"} row onPress={() => {
                this.props.parent.setState({ select: obj })
            }} card={!!select}>
                <SText style={{
                    marginLeft: 8 * lvl,
                }}>{this.renderName(obj)}</SText>
            </SView>
            {this.renderChildrens(obj, lvl + 1)}
        </SView>
    }
    render() {
        return (<SView
            col={"xs-3 md-2"} height card style={{
                overflow: "hidden"
            }}>
            {this.renderItem(this.props.parent.state.data)}
        </SView>
        );
    }
}
