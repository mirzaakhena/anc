(function() {
  'use strict';

  angular
    .module('web')
    .run(runBlock);

  /** @ngInject */
  function runBlock($log) {
    $log.debug('create by mirza akhena');
  }

})();
