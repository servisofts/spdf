import React, { Component } from 'react';
import { View } from 'react-native';
import { SList, SScrollView2, SText, SView } from 'servisofts-component';

export default class WorkSpace extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }

    buildChildrens(childrens) {
        if (!childrens) return null;
        return childrens.map(a => this.buildData(a));
    }

    buildData(data) {
        if (!data) return null;
        switch (data.type) {
            case "text":
                return <SText color='#000' style={data?.style}>{data.childrens}</SText>
            case "page":
                return <View style={{ width: data?.style?.width, height: data?.style?.height, backgroundColor: "#fff" }}  >
                    <View style={{ ...data?.style, backgroundColor: "#fff" }}>
                        {this.buildChildrens(data.childrens)}
                    </View>
                </View>
            default:
                return <SView style={data?.style} >
                    {this.buildChildrens(data.childrens)}
                </SView>
        }

    }
    render() {
        return (<SView flex height style={{
            overflow: "hidden"
        }}>
            <SView flex height backgroundColor='#666'>
                <SScrollView2>
                    {this.buildData(this.props.parent.state.data)}
                </SScrollView2>
            </SView>
        </SView>
        );
    }
}
