<?php

declare(strict_types=1);

namespace DDDCodeSamples\Warranty\Interfaces;

interface ValueObject
{
    public function isSame(ValueObject $object): bool;
}
