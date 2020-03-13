<?php

declare(strict_types=1);

namespace DDDCodeSamples\Tests\Warranty;

use DateInterval;
use DateTime;
use DateTimeImmutable;
use DDDCodeSamples\Warranty\Entity\Contract;
use DDDCodeSamples\Warranty\ValueObject\ContractId;
use DDDCodeSamples\Warranty\ValueObject\Money;
use DDDCodeSamples\Warranty\ValueObject\TermsAndConditions;
use PHPUnit\Framework\TestCase;

/**
 * Class TestClaimsAdjudication
 *
 * @package Warranty
 */
class TestClaimsAdjudication extends TestCase
{
    /**
     * @test
     */
    public function claimsAdjudication(): void
    {
        $contract = new Contract(999, 100.0);
        $contract->effectiveDate = DateTime::createFromFormat('m-d-Y', '08-05-2010');
        $contract->expirationDate = DateTime::createFromFormat('m-d-Y', '08-05-2012');
        $contract->status = Contract::ACTIVE;

        $claim = new Claim(888, 79.0, DateTime::createFromFormat('m-d-Y', '08-05-2010'));

        $adjudicator = new ClaimsAdjustableService();
        $adjudicator->adjudicate($contract, $claim);

        $this->assertCount(1, $contract->getClaims());
    }

    /**
     * @test
     */
    public function claimsAdjudicationForExpiredContract()
    {
        $pendingContract = new Contract(999, 100.0);
        $pendingContract->effectiveDate = DateTime::createFromFormat('m-d-Y', '08-05-2010');
        $pendingContract->expirationDate = DateTime::createFromFormat('m-d-Y', '08-05-2012');
        $pendingContract->status = Contract::EXPIRED;

        $claim = new Claim(888, 79.0, DateTime::createFromFormat('m-d-Y', '08-05-2010'));

        $adjudicator = new ClaimsAdjustableService();
        $adjudicator->adjudicate($pendingContract, $claim);

        $this->assertCount(0, $pendingContract->getClaims());
    }

    /**
     * @test
     */
    public function claimsAdjudicationForInvalidClaim()
    {
        $contract = new Contract(999, 100.0);
        $contract->effectiveDate = DateTime::createFromFormat('m-d-Y', '08-05-2010');
        $contract->expirationDate = DateTime::createFromFormat('m-d-Y', '08-05-2012');
        $contract->status = Contract::ACTIVE;

        $claim = new Claim(888, 81.0, DateTime::createFromFormat('m-d-Y', '08-05-2010'));

        $adjudicator = new ClaimsAdjustableService();
        $adjudicator->adjudicate($contract, $claim);

        $this->assertCount(0, $contract->getClaims());
    }

    /**
     * @test
     */
    public function claimsAdjudicationForPendingContract()
    {
        $pendingContract = new Contract(999, 100.0);
        $pendingContract->effectiveDate = DateTime::createFromFormat('m-d-Y', '08-05-2010');
        $pendingContract->expirationDate = DateTime::createFromFormat('m-d-Y', '08-05-2012');
        $pendingContract->status = Contract::PENDING;

        $claim = new Claim(888, 79.0, DateTime::createFromFormat('m-d-Y', '08-05-2010'));

        $adjudicator = new ClaimsAdjustableService();
        $adjudicator->adjudicate($pendingContract, $claim);

        $this->assertCount(0, $pendingContract->getClaims());
    }

    /**
     * @test
     */
    public function claimsAdjudicationNew(): void
    {
        $effectiveDate = DateTimeImmutable::createFromFormat('m-d-Y', '08-05-2010');
        $expirationDate = DateTimeImmutable::createFromFormat('m-d-Y', '08-05-2012');
        $purchaseDate = DateTimeImmutable::createFromFormat('m-d-Y', '08-05-2010');
        $inStoreGuaranteeDuration = DateInterval::createFromDateString('90 days');
        $termsAndConditions = TermsAndConditions::set(
            $effectiveDate,
            $expirationDate,
            $purchaseDate,
            $inStoreGuaranteeDuration
        );

        $contractId = ContractId::createFromString('ABCD-1234');
        $purchasePrice = Money::USD(10000);
        $contract = new Contract($contractId, $purchasePrice, $termsAndConditions);


        $claimId = ClaimId::generate();
        $claimAmount = Money::USD(7900);
        $claim = new Claim($claimId, $claimAmount, DateTimeImmutable::createFromFormat('m-d-Y', '08-05-2010'));

        $adjudicator = new ClaimsAdjustableService();
        $adjudicator->adjudicate($contract, $claim);

        $this->assertCount(1, $contract->getClaims());
    }
}
