import React, { Component } from 'react';
import { SHr, SInput, SList, SText, SView } from 'servisofts-component';

const stylesProps = ["width", "height", "fontSize", "alignItems", "borderWidth", "flexDirection", "margin", "padding"]
export default class ItemProperties extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }
    render() {
        const { type, style } = this.props.parent.state.select;
        return (<SView
            col={"xs-3 md-2"} height card padding={4} style={{
                overflow: "hidden"
            }}>
            <SInput label={"type"} value={type} />
            <SInput label={"value"} />
            <SHr />
            <SText>Style</SText>
            <SHr />
            {stylesProps.map(sp => <SInput label={sp} value={style[sp]??""} />)}
        </SView>
        );
    }
}
