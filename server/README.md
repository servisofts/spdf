# Servisofts PDF


Para crear un pdf es necesario construir un JSON con el contenido.

## Elemento
| Atributo  | Tipo    | Valor Predeterminado |
| --------- | ------- | -------------------- |
| type      | String  |                      |
| debug     | Boolean |                      |
| style     | {}      |                      |
| childrens | []      |                      |


## Style
| Atributo       | Tipo   | Valor Predeterminado |
| -------------- | ------ | -------------------- |
| width          | float  | 0                    |
| height         | float  | 0                    |
| margin         | float  | 0                    |
| marginTop      | float  | 0                    |
| marginBottom   | float  | 0                    |
| marginLeft     | float  | 0                    |
| marginRight    | float  | 0                    |
| padding        | float  | 0                    |
| paddingTop     | float  | 0                    |
| paddingBottom  | float  | 0                    |
| paddingLeft    | float  | 0                    |
| paddingRight   | float  | 0                    |
| borderWidth    | float  | 0                    |
| borderRadius   | float  | 0                    |
| borderColor    | String | "#000000"            |
| flex           | float  | 0                    |
| fontSize       | float  | 12                   |
| alignItems     | String | "start"              |
| flexDirection  | String | "column" "row"       |
| justifyContent | String | "start"              |




### pages

Para crear un nuevo documento iniciamos creando objeto JSON con el primer elemento de tipo "page".
```json
    {
        "type": "page",
        "style":{
            "width": 300,
        },
        "childrens":[
            ...
        ]
    }
```