
declare module "\*.json" {
    const content: any;
    export default content;
}
declare module '*.svg?inline' {
    const content: any
    export default content
}
declare module "*.svg" {
    const content: React.FunctionComponent<React.SVGAttributes<SVGElement>>;

    export default content;
}

declare module 'react-native-svg' {
    import Svg, { Line, Rect, Svg, Circle, ClipPath, Defs, Ellipse, ForeignObject, Image, G, LinearGradient, Mask, Marker, Path, Pattern, Polygon, Polyline, Stop, SvgAst, SvgCss, Text, TextPath, SvgXml } from 'react-native-svg';
    export { Svg, Line, Path, Rect, Circle, ClipPath, Defs, Ellipse, ForeignObject, Image, G, LinearGradient, Mask, Marker, Pattern, Polygon, Polyline, Stop, SvgAst, SvgCss, Text, TextPath, SvgXml };
    export default Svg;
}
declare module 'servisofts-socket' {
    import SSocket from 'servisofts-socket';
    export default SSocket;
}
