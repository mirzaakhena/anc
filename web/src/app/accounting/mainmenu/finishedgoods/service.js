(function() {
    'use strict';

    angular
        .module('web')
        .factory('FinishedGoodsService', FinishedGoodsService);

    /** @ngInject */
    function FinishedGoodsService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/finishedgoods/:finishedgoodsId');
    }
})();
