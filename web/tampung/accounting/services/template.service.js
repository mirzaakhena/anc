(function() {
    'use strict';

    angular
        .module('web')
        .factory('TemplateService', TemplateService);

    /** @ngInject */
    function TemplateService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/template/:templateId', {
            
        });
    }

})();
