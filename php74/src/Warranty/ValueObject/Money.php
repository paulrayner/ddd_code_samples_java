<?php

declare(strict_types=1);

namespace DDDCodeSamples\Warranty\ValueObject;

use DDDCodeSamples\Warranty\Exception\CurrencyMismatchException;
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

    public function add(Money $otherMoney): Money
    {
        if ($this->currency->isSame($otherMoney->currency)) {
            $newAmount = (string)((int)$this->amount + (int)$otherMoney->amount); // Don't do this for real, use a proper number-string math library!
            return new static($newAmount, $this->currency);
        }
        throw new CurrencyMismatchException();
    }

    public function greaterThan(Money $otherMoney): bool
    {
        return (int)$this->amount > (int)$otherMoney->amount; // Don't do this for real, use a proper number-string math library!
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

    public function multiply(float $multiplier): Money
    {
        $newAmount = (string)((int)$this->amount * $multiplier); // Don't do this for real, use a proper number-string math library!
        return new static($newAmount, $this->currency);
    }

    public function subtract(Money $otherMoney): Money
    {
        if ($this->currency->isSame($otherMoney->currency)) {
            $newAmount = (string)((int)$this->amount - (int)$otherMoney->amount); // Don't do this for real, use a proper number-string math library!
            return new static($newAmount, $this->currency);
        }
        throw new CurrencyMismatchException();
    }
}
