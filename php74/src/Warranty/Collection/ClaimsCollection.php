<?php

declare(strict_types=1);

namespace DDDCodeSamples\Warranty\Collection;

use DDDCodeSamples\Warranty\Entity\Claim;
use DDDCodeSamples\Warranty\ValueObject\Money;

/**
 * Class ClaimsCollection
 *
 * @package DDDCodeSamples\Warranty\Collection
 */
class ClaimsCollection extends Collection
{
    public function __construct(Claim ...$claims)
    {
        $this->items = $claims;
    }

    public function add(Claim $claim): void
    {
        $this->items[] = $claim;
    }

    public function calculateClaimsTotal(): Money
    {
        return array_reduce(
            $this->items,
            static function (Money $runningTotal, Claim $claim) {
                return $runningTotal->add($claim->getClaimAmount());
            },
            Money::USD(0)
        );
    }

}
