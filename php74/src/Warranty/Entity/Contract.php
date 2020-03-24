<?php

declare(strict_types=1);

namespace DDDCodeSamples\Warranty\Entity;

use DDDCodeSamples\Warranty\Collection\ClaimsCollection;
use DDDCodeSamples\Warranty\Exception\ContractException;
use DDDCodeSamples\Warranty\ValueObject\ContractId;
use DDDCodeSamples\Warranty\ValueObject\Money;
use DDDCodeSamples\Warranty\ValueObject\TermsAndConditions;

/**
 * Class Contract
 *
 * @package DDDCodeSamples\Warranty\Entity
 */
final class Contract
{
    private const LIMIT_OF_LIABILITY = 0.8;
    private ContractId $id;
    private Money $purchasePrice;
    private TermsAndConditions $termsAndConditions;
    private ClaimsCollection $claims;

    public function __construct(ContractId $id, Money $purchasePrice, TermsAndConditions $termsAndConditions)
    {
        $this->id = $id;
        $this->purchasePrice = $purchasePrice;
        $this->termsAndConditions = $termsAndConditions;
        $this->claims = new ClaimsCollection();
    }

    public function add(Claim $claim): void
    {
        if ($this->claimWithinLimitOfLiability($claim) && $this->claimIsTimely($claim)) {
            $this->claims->add($claim);
        } else {
            throw new ContractException('Contract is not active or amount is less than limit of liability');
        }
    }

    public function claimWithinLimitOfLiability(Claim $claim): bool
    {
        $currentClaimsTotal = $this->claims->calculateClaimsTotal();
        return $this->purchasePrice->subtract($currentClaimsTotal)
                                   ->multiply(self::LIMIT_OF_LIABILITY)
                                   ->greaterThan($claim->getClaimAmount());
    }

    public function claimIsTimely(Claim $claim): bool
    {
        return $this->termsAndConditions->isActive($claim->getClaimDate());
    }

    public function extendAnnualSubscription(): void
    {
        $this->termsAndConditions = $this->termsAndConditions->extendAnnually();
    }

    public function getClaims(): ClaimsCollection
    {
        return $this->claims;
    }

    public function getId(): ContractId
    {
        return $this->id;
    }

    public function getPurchasePrice(): Money
    {
        return $this->purchasePrice;
    }

    public function getTermsAndConditions(): TermsAndConditions
    {
        return $this->termsAndConditions;
    }

}
