(function() {
    'use strict';

    angular
        .module('web')

    .directive('ngPlaceholder', placeholder);

    /** @ngInject */
    function placeholder($document) {

        return {
            restrict: 'A',
            scope: {
                placeholder: '=ngPlaceholder'
            },
            link: function(scope, elem, attr) {
                scope.$watch('placeholder', function() {
                    elem[0].placeholder = scope.placeholder;
                });
            }
        }

    }



})();
