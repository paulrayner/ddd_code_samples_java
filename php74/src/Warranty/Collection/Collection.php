<?php

declare(strict_types=1);

namespace DDDCodeSamples\Warranty\Collection;

use ArrayIterator;
use IteratorAggregate;

/**
 * Class Collection
 *
 * @package DDDCodeSamples\Warranty\Collection
 */
abstract class Collection implements IteratorAggregate
{
    protected $items;

    public function getIterator(): ArrayIterator
    {
        return new ArrayIterator($this->items);
    }

    public function toArray(): array
    {
        return $this->items;
    }
}
