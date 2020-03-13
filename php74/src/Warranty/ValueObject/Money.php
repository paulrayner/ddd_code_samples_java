<?php

declare(strict_types=1);

namespace DDDCodeSamples\Warranty\ValueObject;

use DDDCodeSamples\Warranty\Interfaces\ValueObject;

/**
 * Class Money
 *
 * @package DDDCodeSamples\Warranty\ValueObject
 */
final class Money implements ValueObject
{
    private string $amount;
    private Currency $currency;

    private function __construct(string $amount, Currency $currency)
    {
        $this->amount = $amount;
        $this->currency = $currency;
    }

    public static function USD(int $int): self
    {
        return new static((string)$int, Currency::USD());
    }

    public function isSame(ValueObject $object): bool
    {
        return $object instanceof self &&
            $this->currency->isSame($object->getCurrency()) &&
            $this->amount === $object->getAmount();
    }

    public function getCurrency(): Currency
    {
        return $this->currency;
    }

    public function getAmount(): string
    {
        return $this->amount;
    }
}
