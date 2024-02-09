
export type PDFElementType = {
    type: any,
    children?: any,
    style?: PDFElementStyleType
}

export type PDFElementStyleType = {
    width: any,
    height: any,
    margin: any,
    marginTop: any,
    marginBottom: any,
    marginLeft: any,
    marginRight: any,
    padding: any,
    paddingTop: any,
    paddingBottom: any,
    paddingLeft: any,
    paddingRight: any,
    borderWidth: any,
    borderRadius: any,
    borderColor: any,
    fontWeight: any,
    fontSize: any,
    flex: number,
    color: any,
    font: any,
    alignItems: any,
    flexDirection: any,
    justifyContent: any
}