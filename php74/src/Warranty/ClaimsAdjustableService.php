<?php

declare(strict_types=1);

namespace DDDCodeSamples\Warranty;

/**
 * Class ClaimsAdjustableService
 *
 * @package DDDCodeSamples\Warranty
 */
class ClaimsAdjustableService
{
    public function adjudicate(Entity\Contract $contract, Entity\Claim $claim): void
    {
        $contract->add($claim);
    }
}
