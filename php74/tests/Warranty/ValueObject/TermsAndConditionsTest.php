<?php

declare(strict_types=1);

namespace DDDCodeSamples\Tests\Warranty\ValueObject;

use DateInterval;
use DateTimeImmutable;
use DDDCodeSamples\Warranty\ValueObject\TermsAndConditions;
use PHPUnit\Framework\TestCase;

/**
 * Class TermsAndConditionsTest
 *
 * @package DDDCodeSamples\Tests\Warranty\ValueObject
 */
class TermsAndConditionsTest extends TestCase
{
    /**
     * @test
     */
    public function extendAnnually()
    {
        $termsAndConditions = TermsAndConditions::set(
            DateTimeImmutable::createFromFormat('m-d-Y', '08-05-2010'),
            DateTimeImmutable::createFromFormat('m-d-Y', '08-05-2012'),
            DateTimeImmutable::createFromFormat('m-d-Y', '08-05-2010'),
            DateInterval::createFromDateString('90 days')
        );
        $expectedAnnuallyExtendedTerms = TermsAndConditions::set(
            DateTimeImmutable::createFromFormat('m-d-Y', '08-05-2010'),
            DateTimeImmutable::createFromFormat('m-d-Y', '08-05-2013'),
            DateTimeImmutable::createFromFormat('m-d-Y', '08-05-2010'),
            DateInterval::createFromDateString('90 days')
        );
        $this->assertEquals($expectedAnnuallyExtendedTerms, $termsAndConditions->extendAnnually());
    }
}
