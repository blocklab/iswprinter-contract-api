# Init project

    ./init.sh && mvn package

# Run project

    ./run.sh

# Generate contract from submodule

    ./scripts/generate_contract_wrapper.sh

# Docs

![Dependency Graph](docs/classes.svg)

# Update class structure

    dot -Tsvg docs/classes.gv -o docs/classes.svg

# Example usage

```
testrpc --account='0xc1371aab5c82b4d1358049ef50768da9f2adfc476cd87668f1e6762f76eea367,5000000000000000000000' --account='0xdb3083055c72ea2528902aae0ff8f9ee488ad9bf009d359da0db2824c9c45fe8,200000000000000000000' --account="0x9ac1f2f2cbb0d43909f1fa69554994fc63a1e5ff0e028f806fe035ac7cb78590,1000000000000000000000" --account="0x864c51dd13443614d0ff6949a3bab604d2664370dfc3ddb0f35b30e324872f51,3000000000000000000000" --account='0x6eaf6c1191b090631668ad8db7dd7f8b5d703f8f5e7315a06f775d030c33829b,5000000000000000000000'

./run.sh

curl localhost:8080/printables/somegcode/0x38588822bea476d5e1d56cfc9ce9781fe5262196

node scripts/buy_prints.js

curl localhost:8080/printables/somegcode/0x38588822bea476d5e1d56cfc9ce9781fe5262196

curl -X DELETE localhost:8080/printables/somegcode/0x38588822bea476d5e1d56cfc9ce9781fe5262196

curl localhost:8080/printables/somegcode/0x38588822bea476d5e1d56cfc9ce9781fe5262196
```
