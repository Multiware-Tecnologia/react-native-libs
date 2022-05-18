# react-native-gerencianet

A module to integrate gerencianet api sdk for android

## Installation

```sh
run npm install react-native-libs
```

## Android configs

```sh
Add this line to your settings.gradle file 
include ':react-native-gerencianet'
project(':react-native-gerencianet').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-libs/react-native-gerencianet/android')

and this line to your app/build.gradle file
implementation project(':react-native-gerencianet')

remember to import the package to your MainApplication.java file
```

## Usage

```js
import { generateHash } from "react-native-gerencianet";

// ...

const result = await generateHash(cardData);
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
