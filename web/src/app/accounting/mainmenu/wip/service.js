(function() {
    'use strict';

    angular
        .module('web')
        .factory('WIPService', WIPService)
        .factory('WIPStartService', WIPStartService)
        .factory('WIPDetailService', WIPDetailService)
        .factory('WIPFinishService', WIPFinishService);

    /** @ngInject */
    function WIPService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/workinprocessbalance/:id');
    }

    /** @ngInject */
    function WIPStartService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/workinprocessbalance/start');
    }

    /** @ngInject */
    function WIPFinishService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/workinprocessbalance/finish');
    }

    /** @ngInject */
    function WIPDetailService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/workinprocessbalance/detail/:id');
    }

})();
