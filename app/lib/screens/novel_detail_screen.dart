import 'package:flutter/material.dart';

class NovelDetailScreen extends StatefulWidget {
  final String title, thumb, id;

  const NovelDetailScreen({
    super.key,
    required this.title,
    required this.thumb,
    required this.id,
  });

  @override
  State<NovelDetailScreen> createState() => _NovelDetailScreenState();
}

class _NovelDetailScreenState extends State<NovelDetailScreen> {
  @override
  Widget build(BuildContext context) {
    return const Placeholder();
  }
}
