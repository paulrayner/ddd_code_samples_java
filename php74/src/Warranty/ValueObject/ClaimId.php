<?php

declare(strict_types=1);

namespace DDDCodeSamples\Warranty\ValueObject;

use DDDCodeSamples\Warranty\Interfaces\EntityId;
use DDDCodeSamples\Warranty\Interfaces\ValueObject;

/**
 * Class ClaimId
 *
 * @package DDDCodeSamples\Warranty\ValueObject
 */
final class ClaimId implements EntityId, ValueObject
{
    private int $id;

    private function __construct(int $id)
    {
        $this->id = $id;
    }

    public static function createFromInteger(int $id): self
    {
        return new static($id);
    }

    public function isSame(ValueObject $object): bool
    {
        return $object instanceof self && $this->id === $object->getId();
    }

    public function getId(): int
    {
        return $this->id;
    }
}
