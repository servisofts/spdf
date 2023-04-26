import { SPage, SPageListProps } from 'servisofts-component';

import Root from './root';
import ajustes from './ajustes';
import test from './test';

export default SPage.combinePages("/", {
    "": Root,
    test,
    ...ajustes,
});