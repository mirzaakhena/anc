(function() {
    'use strict';

    angular
        .module('web')
        .factory('JournalService', JournalService);

    /** @ngInject */
    function JournalService($resource, SERVER_PATH) {
        return $resource(SERVER_PATH + '/journal/:journalId', {
            date: '@date'
        });
    }

})();
