// (function() {
//     'use strict';

//     angular
//         .module('web')
//         .factory('InventoryService', InventoryService)
//         .factory('InventoryChildService', InventoryChildService);

//     /** @ngInject */
//     function InventoryService($resource, SERVER_PATH) {
//         return $resource(SERVER_PATH + '/inventory/:accountId', {
//             date: '@date'
//         });
//     }

//     /** @ngInject */
//     function InventoryChildService($resource, SERVER_PATH) {
//         return $resource(SERVER_PATH + '/inventory/:accountId/child', {
//             date: '@date'
//         });
//     }
// })();
