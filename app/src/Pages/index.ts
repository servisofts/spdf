import { SPage, SPageListProps } from 'servisofts-component';

import Root from './root';
import ajustes from './ajustes';

export default SPage.combinePages("/", {
    "": Root,
    ...ajustes,
});