<?php

declare(strict_types=1);

namespace DDDCodeSamples\Warranty\ValueObject;

use DDDCodeSamples\Warranty\Interfaces\EntityId;
use DDDCodeSamples\Warranty\Interfaces\ValueObject;

/**
 * Class ContractId
 *
 * @package DDDCodeSamples\Warranty\ValueObject
 */
final class ContractId implements EntityId, ValueObject
{
    private string $id;

    private function __construct(string $id)
    {
        $this->id = $id;
    }

    public static function createFromString(string $id): self
    {
        return new static($id);
    }

    public static function generateUuid(): self
    {
        return new static('insert UUID library function call here!');
    }

    public function isSame(ValueObject $object): bool
    {
        return $object instanceof self && $this->id === $object->getId();
    }

    public function getId(): string
    {
        return $this->id;
    }
}
