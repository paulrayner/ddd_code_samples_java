<?php

declare(strict_types=1);

namespace DDDCodeSamples\Tests\Warranty\Collection;

use DateTimeImmutable;
use DDDCodeSamples\Warranty\Collection\ClaimsCollection;
use DDDCodeSamples\Warranty\Entity\Claim;
use DDDCodeSamples\Warranty\ValueObject\ClaimId;
use DDDCodeSamples\Warranty\ValueObject\Money;
use PHPUnit\Framework\TestCase;

/**
 * Class ClaimsCollectionTest
 *
 * @package DDDCodeSamples\Tests\Warranty\Collection
 */
class ClaimsCollectionTest extends TestCase
{
    /**
     * @test
     */
    public function calculateClaimsTotal()
    {
        $claim1 = new Claim(
            ClaimId::createFromInteger(123),
            Money::USD(1000),
            DateTimeImmutable::createFromFormat('m-d-Y', '08-06-2010')
        );

        $claim2 = new Claim(
            ClaimId::createFromInteger(124),
            Money::USD(250),
            DateTimeImmutable::createFromFormat('m-d-Y', '08-07-2010')
        );

        $claim3 = new Claim(
            ClaimId::createFromInteger(125),
            Money::USD(543),
            DateTimeImmutable::createFromFormat('m-d-Y', '08-08-2010')
        );
        $claimsCollection = new ClaimsCollection($claim1, $claim2, $claim3);

        $this->assertEquals(Money::USD(1793), $claimsCollection->calculateClaimsTotal());
    }
}
