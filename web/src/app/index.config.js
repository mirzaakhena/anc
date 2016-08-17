(function() {
    'use strict';

    angular
        .module('web')
        .config(config)
        .service('APIInterceptor', interceptorConfig);

    /** @ngInject */
    function config($httpProvider, $logProvider) {

        $httpProvider.interceptors.push('APIInterceptor');

        // Enable log
        $logProvider.debugEnabled(true);

    }

    /** @ngInject */
    function interceptorConfig($rootScope) {

        this.responseError = function(response) {
            if (response.status === 500) {                
                // sweet.show({
                //     title: 'Error',
                //     type: 'error',
                //     text: response.data.message
                // });
                alert(response.data.message);
            }
            return response;
        };

    }

})();
