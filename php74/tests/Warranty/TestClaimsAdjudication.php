<?php

declare(strict_types=1);

namespace DDDCodeSamples\Tests\Warranty;

use DateInterval;
use DateTimeImmutable;
use DDDCodeSamples\Warranty\ClaimsAdjustableService;
use DDDCodeSamples\Warranty\Entity\Claim;
use DDDCodeSamples\Warranty\Entity\Contract;
use DDDCodeSamples\Warranty\Exception\ContractException;
use DDDCodeSamples\Warranty\ValueObject\ClaimId;
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


        $claimId = ClaimId::createFromInteger(888);
        $claimAmount = Money::USD(7900);
        $claim = new Claim($claimId, $claimAmount, DateTimeImmutable::createFromFormat('m-d-Y', '08-05-2010'));

        $adjudicator = new ClaimsAdjustableService();
        $adjudicator->adjudicate($contract, $claim);

        $this->assertCount(1, $contract->getClaims());
    }

    /**
     * @test
     */
    public function claimsAdjudicationForClaimThatExceedsLimitOfLiability()
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
        $expiredContract = new Contract($contractId, $purchasePrice, $termsAndConditions);


        $claimId = ClaimId::createFromInteger(888);
        $claimAmount = Money::USD(8100);
        $claim = new Claim($claimId, $claimAmount, DateTimeImmutable::createFromFormat('m-d-Y', '08-05-2010'));

        $adjudicator = new ClaimsAdjustableService();
        try {
            $adjudicator->adjudicate($expiredContract, $claim);
        } catch (\Exception $e) {
            $this->assertInstanceOf(ContractException::class, $e);
        }

        $this->assertCount(0, $expiredContract->getClaims());
    }

    /**
     * @test
     */
    public function claimsAdjudicationForExpiredContract()
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
        $expiredContract = new Contract($contractId, $purchasePrice, $termsAndConditions);


        $claimId = ClaimId::createFromInteger(888);
        $claimAmount = Money::USD(7900);
        $claim = new Claim($claimId, $claimAmount, DateTimeImmutable::createFromFormat('m-d-Y', '08-06-2012'));

        $adjudicator = new ClaimsAdjustableService();
        try {
            $adjudicator->adjudicate($expiredContract, $claim);
        } catch (\Exception $e) {
            $this->assertInstanceOf(ContractException::class, $e);
        }

        $this->assertCount(0, $expiredContract->getClaims());
    }

    /**
     * @test
     */
    public function claimsAdjudicationForMultipleClaimsThatExceedsLimitOfLiability()
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
        $expiredContract = new Contract($contractId, $purchasePrice, $termsAndConditions);


        $claimId = ClaimId::createFromInteger(888);
        $claimAmount = Money::USD(7900);
        $claim1 = new Claim($claimId, $claimAmount, DateTimeImmutable::createFromFormat('m-d-Y', '08-06-2010'));

        $claimId = ClaimId::createFromInteger(889);
        $claimAmount = Money::USD(2000);
        $claim2 = new Claim($claimId, $claimAmount, DateTimeImmutable::createFromFormat('m-d-Y', '08-07-2010'));

        $adjudicator = new ClaimsAdjustableService();
        $adjudicator->adjudicate($expiredContract, $claim1);
        try {
            $adjudicator->adjudicate($expiredContract, $claim2);
        } catch (\Exception $e) {
            $this->assertInstanceOf(ContractException::class, $e);
        }

        $this->assertCount(1, $expiredContract->getClaims());
    }

    /**
     * @test
     */
    public function claimsAdjudicationForPendingContract()
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
        $expiredContract = new Contract($contractId, $purchasePrice, $termsAndConditions);


        $claimId = ClaimId::createFromInteger(888);
        $claimAmount = Money::USD(7900);
        $claim = new Claim($claimId, $claimAmount, DateTimeImmutable::createFromFormat('m-d-Y', '08-04-2010'));

        $adjudicator = new ClaimsAdjustableService();
        try {
            $adjudicator->adjudicate($expiredContract, $claim);
        } catch (\Exception $e) {
            $this->assertInstanceOf(ContractException::class, $e);
        }

        $this->assertCount(0, $expiredContract->getClaims());
    }

}
