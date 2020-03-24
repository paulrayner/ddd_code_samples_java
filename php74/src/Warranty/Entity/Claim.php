<?php

declare(strict_types=1);

namespace DDDCodeSamples\Warranty\Entity;

use DateTimeImmutable;
use DDDCodeSamples\Warranty\ValueObject\ClaimId;
use DDDCodeSamples\Warranty\ValueObject\Money;

/**
 * Class Claim
 *
 * @package DDDCodeSamples\Warranty\Entity
 */
class Claim
{
    private ClaimId $id;
    private Money $claimAmount;
    private DateTimeImmutable $claimDate;

    public function __construct(ClaimId $id, Money $claimAmount, DateTimeImmutable $claimDate)
    {
        $this->id = $id;
        $this->claimAmount = $claimAmount;
        $this->claimDate = $claimDate->setTime(0,0);
    }

    public function getClaimAmount(): Money
    {
        return $this->claimAmount;
    }

    public function getClaimDate(): DateTimeImmutable
    {
        return $this->claimDate;
    }

    public function getId(): ClaimId
    {
        return $this->id;
    }

}
