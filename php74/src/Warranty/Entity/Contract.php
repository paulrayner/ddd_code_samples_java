<?php

declare(strict_types=1);

namespace DDDCodeSamples\Warranty\Entity;

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
        $this->claims->push($claim);
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
