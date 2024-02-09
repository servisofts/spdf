
const instaceType = (a) => {
    try {
        return a.type.bind(this, a.props).apply();
    } catch (e) {
        return null;
    }

}

const loadChildrens = (a) => {
    try {
        let childrens = [];
        if (a.props.children) {
            if (Array.isArray(a.props.children)) {
                a.props.children.map(a => {
                    let sf = toJson(a);
                    if (sf) {
                        if (Array.isArray(sf)) {
                            sf.map(c=>{
                                childrens.push(c)    
                            })
                        } else {
                            childrens.push(sf)
                        }
                    }
                })
            } else {
                let sf = toJson(a.props.children);
                if (sf) {
                    childrens.push(sf)
                }
            }

        }
        return childrens;
    } catch (e) {
        console.error(e);
        return null;
    }
}
const toJson = (a) => {
    if (!a) return null;
    let JSON_COMPONENT = null
    if (a.type) {
        JSON_COMPONENT = instaceType(a);
        if (JSON_COMPONENT) {
            if(!JSON_COMPONENT.childrens){
                JSON_COMPONENT.childrens = loadChildrens(a)
            }
        }
    } else {
        if (Array.isArray(a)) {
            JSON_COMPONENT = [];
            a.map(b => {
                let JSON_TEMP = instaceType(b);
                if (JSON_TEMP) {
                    if(!JSON_TEMP.childrens){
                        JSON_TEMP.childrens = loadChildrens(b)
                    }
                }
                JSON_COMPONENT.push(JSON_TEMP)
            })
        }
    }

    return JSON_COMPONENT;

}
export default toJson