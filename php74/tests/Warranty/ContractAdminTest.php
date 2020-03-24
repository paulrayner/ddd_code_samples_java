<?php

declare(strict_types=1);

namespace DDDCodeSamples\Tests\Warranty;

use DateInterval;
use DateTimeImmutable;
use DDDCodeSamples\Warranty\Entity\Claim;
use DDDCodeSamples\Warranty\Entity\Contract;
use DDDCodeSamples\Warranty\ValueObject\ClaimId;
use DDDCodeSamples\Warranty\ValueObject\ContractId;
use DDDCodeSamples\Warranty\ValueObject\Money;
use DDDCodeSamples\Warranty\ValueObject\TermsAndConditions;
use PHPUnit\Framework\TestCase;

/**
 * Class ContractAdminTest
 *
 * @package DDDCodeSamples\Tests\Warranty
 */
class ContractAdminTest extends TestCase
{
    private DateTimeImmutable $effectiveDate;
    private DateTimeImmutable $expirationDate;
    private DateTimeImmutable $purchaseDate;
    private DateInterval $inStoreGuaranteeDuration;
    private TermsAndConditions $termsAndConditions;
    private ContractId $contractId;
    private Money $purchasePrice;
    private Contract $contract;

    /**
     * @test
     */
    public function claimExceedsLimitOfLiability()
    {
        $largeClaim = new Claim(
            ClaimId::createFromInteger(123),
            Money::USD(8001),
            DateTimeImmutable::createFromFormat('m-d-Y', '08-06-2010')
        );
        $this->assertFalse($this->contract->claimWithinLimitOfLiability($largeClaim));
    }

    /**
     * @test
     */
    public function claimIsTimely()
    {
        $timelyClaim = new Claim(
            ClaimId::createFromInteger(123),
            Money::USD(1000),
            DateTimeImmutable::createFromFormat('m-d-Y', '08-06-2010')
        );
        $this->assertTrue($this->contract->claimIsTimely($timelyClaim));
    }

    /**
     * @test
     */
    public function claimIsTooEarly()
    {
        $earlyClaim = new Claim(
            ClaimId::createFromInteger(123),
            Money::USD(1000),
            DateTimeImmutable::createFromFormat('m-d-Y', '08-04-2010')
        );
        $this->assertFalse($this->contract->claimIsTimely($earlyClaim));
    }

    /**
     * @test
     */
    public function claimIsTooLate()
    {
        $lateClaim = new Claim(
            ClaimId::createFromInteger(123),
            Money::USD(1000),
            DateTimeImmutable::createFromFormat('m-d-Y', '08-06-2012')
        );
        $this->assertFalse($this->contract->claimIsTimely($lateClaim));
    }

    /**
     * @test
     */
    public function claimWithinLimitOfLiability()
    {
        $smallClaim = new Claim(
            ClaimId::createFromInteger(123),
            Money::USD(1000),
            DateTimeImmutable::createFromFormat('m-d-Y', '08-06-2010')
        );
        $this->assertTrue($this->contract->claimWithinLimitOfLiability($smallClaim));
    }

    /**
     * @test
     */
    public function contractIsSetupCorrectly()
    {
        $this->assertEquals($this->contractId, $this->contract->getId());
        $this->assertEquals($this->purchasePrice, $this->contract->getPurchasePrice());
        $this->assertEquals($this->termsAndConditions, $this->contract->getTermsAndConditions());
        $this->assertCount(0, $this->contract->getClaims());
    }

    /**
     * @test
     */
    public function extendAnnualSubscription()
    {
        $expectedAnnuallyExtendedTerms = TermsAndConditions::set(
            DateTimeImmutable::createFromFormat('m-d-Y', '08-05-2010'),
            DateTimeImmutable::createFromFormat('m-d-Y', '08-05-2013'),
            DateTimeImmutable::createFromFormat('m-d-Y', '08-05-2010'),
            DateInterval::createFromDateString('90 days')
        );
        $this->contract->extendAnnualSubscription();
        $this->assertEquals($expectedAnnuallyExtendedTerms, $this->contract->getTermsAndConditions());
    }

    public function setUp()
    {
        $this->effectiveDate = DateTimeImmutable::createFromFormat('m-d-Y', '08-05-2010');
        $this->expirationDate = DateTimeImmutable::createFromFormat('m-d-Y', '08-05-2012');
        $this->purchaseDate = DateTimeImmutable::createFromFormat('m-d-Y', '08-05-2010');
        $this->inStoreGuaranteeDuration = DateInterval::createFromDateString('90 days');
        $this->termsAndConditions = TermsAndConditions::set(
            $this->effectiveDate,
            $this->expirationDate,
            $this->purchaseDate,
            $this->inStoreGuaranteeDuration
        );

        $this->contractId = ContractId::createFromString('ABCD-1234');
        $this->purchasePrice = Money::USD(10000);
        $this->contract = new Contract($this->contractId, $this->purchasePrice, $this->termsAndConditions);
    }
}
