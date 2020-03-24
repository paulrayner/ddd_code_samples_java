<?php

declare(strict_types=1);

namespace DDDCodeSamples\Warranty\ValueObject;

use DDDCodeSamples\Warranty\Interfaces\ValueObject;

/**
 * Class Currency
 *
 * @package DDDCodeSamples\Warranty\ValueObject
 */
final class Currency implements ValueObject
{
    public const USD = 'USD';
    private string $currencySymbol;

    private function __construct(string $currencySymbol)
    {
        $this->currencySymbol = $currencySymbol;
    }

    public static function USD(): self
    {
        return new static(self::USD);
    }

    public function isSame(ValueObject $object): bool
    {
        return $object instanceof self && $this->currencySymbol === $object->getCurrencySymbol();
    }

    public function getCurrencySymbol(): string
    {
        return $this->currencySymbol;
    }
}

